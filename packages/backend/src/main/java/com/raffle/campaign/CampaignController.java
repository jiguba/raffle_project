// packages/backend/src/main/java/com/raffle/campaign/CampaignController.java
package com.raffle.campaign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

  private final CampaignRepository repo;

  public CampaignController(CampaignRepository repo) {
    this.repo = repo;
  }

  // Request body schema
  public record CreateCampaignReq(
      @NotBlank String name,
      String description,
      String owner
  ) {}

  // Create -> 201 Created + Location header + entity body
  @PostMapping
  public ResponseEntity<Campaign> create(@RequestBody @Valid CreateCampaignReq req,
                                         UriComponentsBuilder ucb) {
    Campaign saved = repo.save(new Campaign(req.name(), req.description(), req.owner()));
    URI location = ucb.path("/campaigns/{id}").buildAndExpand(saved.getId()).toUri();
    return ResponseEntity.created(location).body(saved);
  }

  // List all (consider adding paging later)
  @GetMapping
  public List<Campaign> list() {
    return repo.findAll();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    if (!repo.existsById(id)) {
      return ResponseEntity.notFound().build();  // 404
    }
    repo.deleteById(id);                          // 204
    return ResponseEntity.noContent().build();
  }
}


