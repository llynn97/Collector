package moviegoods.movie.domain.dto.informationShare;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationShareRequestWriteRequestDto {



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
