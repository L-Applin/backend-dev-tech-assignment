package ca.applin.octaveg.assignement.jukes.controller;

import ca.applin.octaveg.assignement.jukes.dto.JukesDto;
import ca.applin.octaveg.assignement.jukes.service.JukesService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class JukesController {

    private final JukesService jukesService;

    @GetMapping("/jukes")
    public ResponseEntity<List<JukesDto>> getJukebox(
            @RequestParam String settingId,
            @RequestParam Optional<String> model,
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> limit) {
        List<JukesDto> jukes = jukesService.getAllJukesBySettingId(settingId, model, offset, limit)
                .get().collect(Collectors.toList());
        return jukes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jukes);
    }

}
