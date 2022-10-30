package com.example.project.direction.service;

import com.example.project.api.dto.DocumentDto;
import com.example.project.direction.entity.Direction;
import com.example.project.pharmacy.dto.PharmacyDto;
import com.example.project.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3; // 약국 최대 검색 갯수
    private static final double RADIUS_KM = 10.0; // 반경 10 km

    private final PharmacySearchService pharmacySearchService;

    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return pharmacySearchService.searchPharmacyDtoList()
                .stream().map(pharmacyDto ->
                    Direction.builder()
                            .inputAddress(documentDto.getAddressName())
                            .inputLatitude(documentDto.getLatitude())
                            .inputLongitude(documentDto.getLongitude())
                            .targetPharmacyName(pharmacyDto.getPharmacyName())
                            .targetAddress(pharmacyDto.getPharmacyAddress())
                            .targetLatitude(pharmacyDto.getLatitude())
                            .targetLongitude(pharmacyDto.getLongitude())
                            .distance(
                                    calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                            pharmacyDto.getLatitude(), pharmacyDto.getLongitude())
                            )
                            .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
