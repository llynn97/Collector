package moviegoods.movie.information_share.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

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
    private String image_url;





}
