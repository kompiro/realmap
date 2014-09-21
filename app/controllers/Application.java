package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import views.html.*;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result upload() {
        return ok(upload.render());
    }

    public static Result postUpload() {
      MultipartFormData body = request().body().asMultipartFormData();
      FilePart picture = body.getFile("picture");
      if (picture != null) {
        String fileName = picture.getFilename();
        String contentType = picture.getContentType();
        File file = picture.getFile();
        try{
          Files.copy(file.toPath(),new File("/Users/kompiro/realmap/" + file.getName()).toPath());
        }catch(IOException ex){
          ex.printStackTrace();
        }
        return ok("File uploaded");
      } else {
        flash("error", "Missing file");
        return redirect(routes.Application.index());
      }
    }

}
