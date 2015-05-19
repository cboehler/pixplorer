package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.UserInfoReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.UserInfoRequest;

import com.google.gson.Gson;

@Path("/userinfo")
public class UserInfo {
	private static final Gson gson = new Gson();	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String userInfo(String json) {
		UserInfoRequest request = gson.fromJson(json, UserInfoRequest.class);
		UserInfoReply reply = new UserInfoReply();
		
		String username = request.getGoogleId();
		
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + username + "'");
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
			return gson.toJson(reply);
		}

		User user = users.get(0);
		reply.setScore(user.getScore());
		reply.getFoundPlaces().addAll(user.getFoundPlaces());
		reply.getFavourites().addAll(user.getFavourites());
		
		return gson.toJson(reply);		
	}
}
