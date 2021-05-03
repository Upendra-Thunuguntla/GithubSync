package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

	static String path = "C:\\Users\\Muralidhar\\Documents\\GitHub\\HackerRank\\";


	public static void main(String[] args) throws IOException, InterruptedException {
		File input = new File("C:\\Users\\Muralidhar\\Downloads\\hackerrank.json");
		StringBuilder sb = new StringBuilder();
		Files.readAllLines(input.toPath()).forEach(x->sb.append(x));
		JSONObject root = new JSONObject(sb.toString());
		JSONArray submissions = root.getJSONArray("submissions");
		List<TestPOJO> testList = new ArrayList<TestPOJO>();

		for (int i=0;i<submissions.length();i++) {
			TestPOJO tp = new TestPOJO(submissions.getJSONObject(i));
			if (!testList.contains(tp) && !tp.getLanguage().equals("text")) {
				testList.add(tp);
			}else {
				if (testList.contains(tp)) {
					TestPOJO oldTp = testList.get(testList.indexOf(tp));
					if (oldTp.getScore() < tp.getScore()) {
						testList.remove(oldTp);
						testList.add(tp);
					}
				}
			}
		}
		testList.forEach(Main::createFile);
		//		System.out.println(testList);

	}

	public static void createFile(TestPOJO tp) {
		File outFile = new File(path+tp.getFileName());
		try {
			Files.write(outFile.toPath(), tp.getCode().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
