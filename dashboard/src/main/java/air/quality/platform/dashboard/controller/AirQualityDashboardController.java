package air.quality.platform.dashboard.controller;

import air.quality.platform.dashboard.model.AirQualityInformation;
import air.quality.platform.dashboard.service.AirQualityDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Air Quality Dashboard", description = "APIs for querying air quality data")
public class AirQualityDashboardController {

    private final AirQualityDashboardService service;

    @Operation(summary = "Get all air quality records sorted by timestamp (latest first)")
    @ApiResponse(responseCode = "200", description = "List of air quality records")
    @GetMapping("/latest")
    public List<AirQualityInformation> getAllSorted() {
        return service.getAllSortedByTimestamp();
    }

    @Operation(summary = "Get the latest air quality record for a given sensor")
    @ApiResponse(responseCode = "200", description = "Latest record for specified sensor")
    @Parameter(name = "sensorId", description = "Sensor ID to filter", required = true)
    @GetMapping("/sensor/{sensorId}")
    public AirQualityInformation getLatestForSensor(@PathVariable String sensorId) {
        return service.getLatestForSensor(sensorId);
    }

    @Operation(summary = "Get air quality records below a score threshold")
    @ApiResponse(responseCode = "200", description = "Low score records retrieved")
    @Parameter(name = "threshold", description = "Score threshold (default: 50)", required = false)
    @GetMapping("/low-scores")
    public List<AirQualityInformation> getLowScores(@RequestParam(defaultValue = "50") double threshold) {
        return service.getLowScores(threshold);
    }

    @Operation(summary = "Get records by score level (e.g., GOOD, MODERATE, POOR)")
    @ApiResponse(responseCode = "200", description = "Records filtered by score level")
    @Parameter(name = "level", description = "Score level to filter", required = true)
    @GetMapping("/score-level/{level}")
    public List<AirQualityInformation> getByScoreLevel(@PathVariable String level) {
        return service.getByScoreLevel(level.toUpperCase());
    }

    @Operation(summary = "Get unnotified air quality records")
    @ApiResponse(responseCode = "200", description = "List of records where notified=true")
    @GetMapping("/notified")
    public List<AirQualityInformation> getNotified() {
        return service.getNotified();
    }

    @Operation(summary = "Get air quality data from the last 24 hours")
    @ApiResponse(responseCode = "200", description = "Recent records from last 24 hours")
    @GetMapping("/last24h")
    public List<AirQualityInformation> getLast24Hours() {
        return service.getLast24Hours();
    }

    @Operation(summary = "Get average air quality score per location")
    @ApiResponse(responseCode = "200", description = "Average score per location")
    @GetMapping("/average-score-by-location")
    public List<Map<String, Object>> averageScoreByLocation() {
        return service.getAverageScoreByLocation();
    }

    @Operation(summary = "Get count of records per score level")
    @ApiResponse(responseCode = "200", description = "Number of records grouped by score level")
    @GetMapping("/score-distribution")
    public List<Map<String, Object>> countByScoreLevel() {
        return service.getCountPerScoreLevel();
    }
}

