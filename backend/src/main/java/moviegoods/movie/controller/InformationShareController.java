package moviegoods.movie.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.informationShare.InformationShareRequestWriteRequestDto;
import moviegoods.movie.domain.dto.informationShare.InformationShareSearchRequestDto;
import moviegoods.movie.domain.dto.informationShare.InformationShareSearchResponseDto;
import moviegoods.movie.service.InformationShareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/information-share")
public class InformationShareController {

  private final InformationShareService informationShareService;

   @PostMapping("/write")
   public ResultResponseDto write(@RequestBody InformationShareRequestWriteRequestDto requestDto){
       ResultResponseDto resultResponseDto = informationShareService.savePost(requestDto);
       return resultResponseDto;

   }

    @GetMapping("/search")
    public ResponseEntity<List<InformationShareSearchResponseDto>> search(@ModelAttribute InformationShareSearchRequestDto requestDto){
        List<InformationShareSearchResponseDto> list=  informationShareService.search(requestDto);
        ResponseEntity<List<InformationShareSearchResponseDto>> entity=new ResponseEntity<>(list, HttpStatus.OK);
        return  entity;

    }


}
