package moviegoods.movie.information_share.request;


import lombok.Data;

import java.util.Date;

@Data
public class InformationShareRequestSearch {
    private String search_word;
    private String cinema_name;
    private String cinema_area;
    private String cinema_branch;
    private String sort_name;

}
