package dad.Api.GlobalStat;

import java.util.HashMap;
import java.util.List;

import io.github.fvarrui.globalstats.GlobalStats;
import io.github.fvarrui.globalstats.model.Rank;
import io.github.fvarrui.globalstats.model.Section;
import io.github.fvarrui.globalstats.model.Stats;

public class GlobalStat {

	String clientId="nL8l9XT08r7gzzsaPC3f2Dhyu1pjqOWOA4aJ23Ja", clientSecret="Rq4qTegwhwyUN6pPvJfkF3UJeEMinWMenRFg5WMn";
	final String token;
	GlobalStats client;
	Stats stats;

	public GlobalStat() throws Exception {
		client = new GlobalStats(clientId, clientSecret);
		token = client.getAccessToken();
		System.out.println(token);
	}

	@SuppressWarnings("serial")
	public void createUser() throws Exception {
		stats = client.createStats("username", new HashMap<String, Object>() {{
			
			put("highscore", 100);
			
		}});
		System.out.println(stats);
	}

	@SuppressWarnings("serial")
	public void updateUser() throws Exception {
		Stats stats = client.updateStats(token, 
				new HashMap<String, Object>() {{
					
				put("highscore", "+20");
				
			}});
		System.out.println(stats);
	}

	public void getUserStadistic() throws Exception {
	Stats stats = client.getStats(token);
	System.out.println(stats);
	}
	
	public void getUserSection() throws Exception {
		Section section = client.getStatsSection(token, "highscore");
		System.out.println(section);
	}
	
	
	public void getLeaderBorad() throws Exception {
		List<Rank> leader=  client.getLeaderboard("highscore", 10);
		System.out.println(leader);
	}

}
