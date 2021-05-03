package src;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

public class TestPOJO implements Comparator<TestPOJO>{

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
	
	public TestPOJO(JSONObject json) {
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
			TestPOJO tp = (TestPOJO) obj;
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

	public int compare(TestPOJO o1, TestPOJO o2) {
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
