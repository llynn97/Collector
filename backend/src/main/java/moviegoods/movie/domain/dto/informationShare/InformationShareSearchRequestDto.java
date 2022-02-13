package moviegoods.movie.domain.dto.informationShare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformationShareSearchRequestDto {
    private String search_word;
    private String cinema_name;
    private String cinema_area;
    private String cinema_branch;
    private String sort_name;

}