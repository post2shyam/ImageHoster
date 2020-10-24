package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String postComment(@RequestParam("comment") String comment,
                              @PathVariable("imageId") Integer imageId,
                              @PathVariable("imageTitle") String imageTitle,
                              HttpSession session,
                              Model model) {

        User loggedInUser = (User) session.getAttribute("loggeduser");
        Image existingImage = imageService.getImage(imageId);

        //Create a new comment
        Comment newComment = new Comment();
        newComment.setUser(loggedInUser);
        newComment.setImage(existingImage);
        newComment.setText(comment);
        newComment.setCreatedDate(new Date());

        commentService.uploadComment(newComment);

        model.addAttribute("image", existingImage);
        model.addAttribute("tags", existingImage.getTags());
        model.addAttribute("comments", existingImage.getComments());

        return "redirect:/images/" + imageId + "/" + imageTitle;
    }
}
