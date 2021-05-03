package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

	static String outputFilesPath = "Path where you need your files to be created \\HackerRank\\";
	static File input = new File("Path to JSON file downloaded");

	public static void main(String[] args) throws IOException, InterruptedException {
		
		StringBuilder sb = new StringBuilder();
		Files.readAllLines(input.toPath()).forEach(x->sb.append(x));
		JSONObject root = new JSONObject(sb.toString());
		JSONArray submissions = root.getJSONArray("submissions");
		List<SubmissionPOJO> submissionsList = new ArrayList<SubmissionPOJO>();

		for (int i=0;i<submissions.length();i++) {
			SubmissionPOJO tp = new SubmissionPOJO(submissions.getJSONObject(i));
			if (!submissionsList.contains(tp) && !tp.getLanguage().equals("text")) {
				submissionsList.add(tp);
			}else {
				if (submissionsList.contains(tp)) {
					SubmissionPOJO oldTp = submissionsList.get(submissionsList.indexOf(tp));
					if (oldTp.getScore() < tp.getScore()) {
						submissionsList.remove(oldTp);
						submissionsList.add(tp);
					}
				}
			}
		}
		submissionsList.forEach(Main::createFile);

	}

	public static void createFile(SubmissionPOJO tp) {
		File outFile = new File(outputFilesPath+tp.getFileName());
		try {
			Files.write(outFile.toPath(), tp.getCode().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


class SubmissionPOJO implements Comparator<SubmissionPOJO>{

	private String contest;
	private String challenge;
	private String code;
	private String language;
	private String fileName;

	private Integer score;
	
	private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
	
	Map<String, String> map = Stream.of(new String[][] {
		  { "python", ".py" }, 
		  { "python3", ".py" },
		  { "oracle", ".sql" },
		  { "c", ".c" },
		  { "java", ".java" },
		  { "java8", ".java" },
		  //new file mappings can be added here
		}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	public SubmissionPOJO(JSONObject json) {
		this.challenge = json.getString("challenge");
		this.code = json.getString("code");
		this.contest = json.getString("contest");
		this.language = json.getString("language");
		this.score = json.getInt("score");
		
		this.fileName = json.getString("challenge")+map.get(this.language);
		for (char chr : ILLEGAL_CHARACTERS) {
			this.fileName = this.fileName.replace(chr, ' ');
		}
		this.fileName = this.fileName.replace("..",".");
		this.fileName = this.fileName.replace("  "," ");
		this.fileName = this.fileName.trim();
//		this.fileName = this.fileName.replace(" ","");
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (null==obj)
			return false;
		else {
			SubmissionPOJO tp = (SubmissionPOJO) obj;
			return contest.equals(tp.getContest()) && 
					challenge.equals(tp.getChallenge()) && 
					language.equals(tp.language);
		}
	}

	@Override
	public String toString() {
	
//		return language + "  " + contest + "  " + challenge;
		return this.fileName;
	}

	public int compare(SubmissionPOJO o1, SubmissionPOJO o2) {
		if (o1.getScore() > o2.getScore()) {
			return 1;
		}else if (o1.getScore() < o2.getScore()) {
			return -1;
		}else {
			return 0;
		}
	}


	/**
	 * @return the contest
	 */
	public String getContest() {
		return contest;
	}


	/**
	 * @return the challenge
	 */
	public String getChallenge() {
		return challenge;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}


	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}


	/**
	 * @param contest the contest to set
	 */
	public void setContest(String contest) {
		this.contest = contest;
	}


	/**
	 * @param challenge the challenge to set
	 */
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}


	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}


	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
