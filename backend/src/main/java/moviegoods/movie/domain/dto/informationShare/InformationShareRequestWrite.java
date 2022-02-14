package moviegoods.movie.domain.dto.informationShare;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class InformationShareRequestWrite {



    private Long user_id;
    @NotBlank
    private String title;

    @NotBlank
    private String cinema_name;

    @NotBlank
    private String cinema_area;

    @NotBlank
    private String  cinema_branch;
    @NotBlank
    private String  content;

    private MultipartFile image_url;





}
