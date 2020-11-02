package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;


@Controller	// This means that this class is a Controller
@RequestMapping(path="/users") // This means URL's start with /users (after Application path)
public class UserController {
	@Autowired // This means to get the bean called userRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	@Value("${accesskey}")
    String accesskey;
    @Value("${secretkey}")
    String secretkey;
    @Value("${bucketName}")
    String bucketName;
	// http://localhost:8080/users/add
	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (
		@RequestParam String name
			, @RequestParam String email
			, @RequestParam String password) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		n.setPassword(password);
		userRepository.save(n);
		return "Saved";
	}
	@PostMapping(path="/login") // Map ONLY POST Requests
	public ModelAndView loginUser (
			 @RequestParam String email
			, @RequestParam String password) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = userRepository.findByEmail(email);
		boolean result = n.comparePassword(password);
		if (result == true) {
			return new ModelAndView("editProfile");
		}
		return new ModelAndView("failLogin");
	}
	@PostMapping(value = "/upload")
    public ModelAndView uploads3( @RequestParam(name = "name") String name, @RequestParam(name = "bio") String desc, @RequestParam("photo") MultipartFile image) {
        ModelAndView returnPage = new ModelAndView("error");
        System.out.println("description      " + desc);
        System.out.println(image.getOriginalFilename());
    
        BasicAWSCredentials cred = new BasicAWSCredentials(accesskey, secretkey);
        // AmazonS3Client client=AmazonS3ClientBuilder.standard().withCredentials(new
        // AWSCredentialsProvider(cred)).with
        AmazonS3 client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(cred))
                .withRegion(Regions.US_EAST_1).build();
        try {
            PutObjectRequest put = new PutObjectRequest(bucketName, image.getOriginalFilename(),
                    image.getInputStream(), new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);
            client.putObject(put);

			String imgSrc = "http://" + bucketName + ".s3.amazonaws.com/" + image.getOriginalFilename();
			User n = userRepository.findByEmail("s@no.com");
			n.setImgURL(imgSrc);
			if (!name.equals(null) && !name.equals("")){
				n.setName(name);
			}
			if (!desc.equals(null) && !desc.equals("")){
				n.setBio(desc);
			}
			userRepository.save(n);
			returnPage = new ModelAndView("home");
			returnPage.addObject("pfp", n.getImgURL());
			returnPage.addObject("name", n.getName());
			returnPage.addObject("bio", n.getBio());
           

            //Save this in the DB. 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return returnPage;

    }

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	@GetMapping(path="/user")
	public @ResponseBody Optional<User> getOneUser(@RequestParam Integer id) {
		// This returns a JSON or XML with the users
		return userRepository.findById(id);
		
	}

	@GetMapping(path="/userByName")
	public @ResponseBody User getOneUserByName(@RequestParam String name) {
		return userRepository.findByName(name);
	}

	
	@GetMapping(path="/userByEmail")
	public @ResponseBody User getOneUserByEmail(@RequestParam String email) {
		return userRepository.findByEmail(email);
	}


	@GetMapping(path="/addUser")
	public ModelAndView showPage(){
		return new ModelAndView("signupForm");
	}
	@GetMapping(path="/login")
	public ModelAndView showLogin(){
		return new ModelAndView("login");
	}
	@GetMapping("/home")
    public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		User n = userRepository.findByEmail("s@no.com");
		mv.addObject("pfp", n.getImgURL());
		mv.addObject("name", n.getName());
		mv.addObject("bio", n.getBio());
        return mv;
    }
}