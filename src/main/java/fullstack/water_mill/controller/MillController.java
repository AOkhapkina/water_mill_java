package fullstack.water_mill.controller;

import fullstack.water_mill.bean.MillState;
import fullstack.water_mill.service.MillService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/mill")
public class MillController {

    private final MillService millService;

    public MillController(MillService millService) {
        this.millService = millService;
    }

    @GetMapping
    public MillState state() {
        return millService.getState();
    }

    @PostMapping("water/{capacity}")
    public String addWater(@PathVariable Integer capacity) {// @PathVariable - part of  URL Path
        millService.addWater(capacity);
        return "Millet added";
    }

    @PostMapping("millet/{capacity}")
    public String addMillet(@PathVariable Integer capacity) {
        millService.addMillet(capacity);
        return "Millet added";
    }

    @PostMapping("flour/drop") // selling of flour
    public String dropFlour() {
        millService.dropFlour();
        return "The flour was sold";
    }

}
