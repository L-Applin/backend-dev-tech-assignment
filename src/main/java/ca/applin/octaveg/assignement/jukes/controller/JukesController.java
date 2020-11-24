package ca.applin.octaveg.assignement.jukes.controller;

import ca.applin.octaveg.assignement.jukes.JukesConstant;
import ca.applin.octaveg.assignement.jukes.dto.JukesDto;
import ca.applin.octaveg.assignement.jukes.service.JukesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class JukesController {

    private final JukesService jukesService;

    @ApiOperation(value = "Find Jukebox for particular settings.",
            notes = "",
            response = JukesDto.class,
            responseContainer = "List")
    @GetMapping(JukesConstant.BASE_JUKES_URL)
    public ResponseEntity<List<JukesDto>> getJukebox(
            @ApiParam(value = "The setting to search for", required = true, example = "86506865-f971-496e-9b90-75994f251459") @RequestParam String settingId,
            @ApiParam(value = "A specific jukebox model to search for.", example = "fusion") @RequestParam(required = false) Optional<String> model,
            @ApiParam(value = "Pagination offset") @RequestParam(required = false) Optional<Integer> offset,
            @ApiParam(value = "Pagination limit") @RequestParam(required = false) Optional<Integer> limit) {
        List<JukesDto> jukes = jukesService.getAllJukesBySettingId(settingId, model, offset, limit)
                .getContent();
        return jukes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jukes);
    }

}
