package com.tweetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.tweetapp.entity.DisplayUserPojo;
import com.tweetapp.entity.Login;
import com.tweetapp.entity.Users;
import com.tweetapp.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/v1.0/tweets/register")
	void registerUser(@RequestBody Users user)
	{
		user = userService.registerUser(user);
		System.out.println("from reister"+user);
		
	}
	
	@GetMapping("/api/v1.0/tweets/users/all")
	public List<DisplayUserPojo> getUsersDetails()
	{
		List<DisplayUserPojo> obj =  userService.getUserDetails();
		return obj;
	}
	
	
	@PostMapping("/api/v1.0/tweets/users/search")
	public DisplayUserPojo getUser(@RequestBody Login login)
	{
		DisplayUserPojo obj =  userService.getUserDetail(login.getUserId());
		return obj;
	}
	
	@PutMapping("/updateUser")
	public String updateUser(@RequestBody Users user)
	{
		userService.updateUser(user);
		return "Done";
	}

 /*
  * Login Controller Below
  */
	@PostMapping("/api/v1.0/tweets/login")
	public Login  loginUser(@RequestBody Login login)
	{
		String userId;
		Boolean status = userService.checkCredentials(login);
		if(status == true)
		{
			login.setPassword(null);
		
		}
		else
		{
			login = null;
		}
		
		
		return login;
	}
	
	@PostMapping("api/v1.0/tweets/users/changepassword")
	public Login forgotPassword(@RequestBody Login login)
	{
		    Users user =   userService.findUserById(login.getUserId());
		   if(user != null)
		   {
			   userService.updateUserPassword(login);
			  System.out.println(login.toString());
			   return login;
		}
		   else {
			   login.setPassword(null);
			   login.setUserId(null);
				  System.out.println(login.toString());

			   return login;
		   }
	}
}
