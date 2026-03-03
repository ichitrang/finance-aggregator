package com.acme.finance.analysis;

import com.acme.finance.analysis.dto.*;
import com.acme.finance.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final AnalysisService analysisService;
    private final UserRepository userRepo;

    public AnalysisController(AnalysisService analysisService, UserRepository userRepo) {
        this.analysisService = analysisService;
        this.userRepo = userRepo;
    }

    @GetMapping("/categories")
    public List<CategorySpendDto> spendByCategory(Authentication auth) {
        String email = (String) auth.getPrincipal();
        var user = userRepo.findByEmail(email).orElseThrow();
        return analysisService.spendingByCategory(user.getId(), 30);
    }

    @GetMapping("/trends")
    public TrendDto trends(Authentication auth) {
        String email = (String) auth.getPrincipal();
        var user = userRepo.findByEmail(email).orElseThrow();
        return analysisService.trendsLast30Days(user.getId());
    }
}