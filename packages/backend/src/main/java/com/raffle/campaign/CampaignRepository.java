package com.raffle.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {}

