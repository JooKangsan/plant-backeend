package com.plantcommunity.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "plant_problems")
public class PlantProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @Column(nullable = false)
    private String problem;

    @Column(columnDefinition = "TEXT")
    private String solution;

    // Constructors
    public PlantProblem() {}

    public PlantProblem(Plant plant, String problem, String solution) {
        this.plant = plant;
        this.problem = problem;
        this.solution = solution;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Plant getPlant() { return plant; }
    public void setPlant(Plant plant) { this.plant = plant; }

    public String getProblem() { return problem; }
    public void setProblem(String problem) { this.problem = problem; }

    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }
}
