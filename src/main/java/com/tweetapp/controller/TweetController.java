package com.tweetapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.entity.Like;
import com.tweetapp.entity.Login;
import com.tweetapp.entity.TweetReply;
import com.tweetapp.entity.UserTweet;
import com.tweetapp.kafka.Producer;
import com.tweetapp.service.TweetService;

@CrossOrigin(origins = "*")
@RestController
public class TweetController {

	@Autowired
	TweetService tweetService;
	 public Producer producer;
	 
	  @Autowired TweetController(Producer producer){ this.producer=producer;
	 
	  }  
	@GetMapping("/publish") 
	public String sendMessage(@PathVariable String message)
	   { this.producer.sendMessage(message); 
	   return  "connection established";
	  
	 }

	/*
	 * Below Controllers for Tweet services
	 */
	
	@PostMapping(value = "/api/v1.0/tweets/add")
	public void postTweetReply(@Valid @RequestBody UserTweet userTweet) {
		
		UserTweet result = tweetService.postUserTweet(userTweet);
		
	}

	@PostMapping("/api/v1.0/tweets/myTweet")
	public List<UserTweet> getUserTweet(@RequestBody Login login) {
		return tweetService.getUserTweet(login.getUserId());
	}
	@GetMapping("/api/v1.0/tweets/all")
	public List<UserTweet> getAllTweets() {
		List<UserTweet> obj =  tweetService.getAllTweet();
		return obj;
	}
	
	
	@PostMapping("/api/v1.0/tweets/update")
	public void updateTweet(@RequestBody UserTweet userTweet )
	{
		tweetService.updateTweet(userTweet);
		
	}
	@DeleteMapping("/api/v1.0/tweets/delete/{tweetId}")
	public String DeleteTweet(@PathVariable String tweetId)
	{
		tweetService.deleteTweet(tweetId);
		return "Deleted";
	}
	/*
	 * Below Controllers for tweet replies
	 */
	@PostMapping(value = "/api/v1.0/tweets/reply")
	public String postTweetReply(@RequestBody TweetReply tweetReply) {
		String response;
		TweetReply result = tweetService.postTweetReply(tweetReply);
		if (result!=null) {
			response = "Details registered successfully";
		} else {
			response = "Details not registered successfully";
		}

		return response;
	}

	@PostMapping("/api/v1.0/tweets/getReply")
	public List<TweetReply> getAllReply(@RequestBody UserTweet usertweet) {
		List<TweetReply> replies =  tweetService.getAllReply(usertweet.getTweetId());
	return replies;
	}
	/*
	 * Below controllers mapping for Tweet Like Services
	 */

	@PostMapping(value = "/api/v1.0/tweets/addLike")
	public Like getLikeDetails(@RequestBody Like like) {
		
		Like result = tweetService.postLike(like);

		return  result;
	}

	@GetMapping("/api/v1.0/tweets/getLikes")
	public List<Like> getAllLikes() {
		return tweetService.getAllLikes();
	}
}
