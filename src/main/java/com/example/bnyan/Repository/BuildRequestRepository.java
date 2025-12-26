package com.example.bnyan.Repository;

import com.example.bnyan.Model.BuildRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildRequestRepository extends JpaRepository<BuildRequest, Integer> {

    BuildRequest getBuildRequestById(Integer id);

    @Query("select br from BuildRequest br where br.status = ?1")
    List<BuildRequest> getBuildRequestsByStatus(String status);

    @Query("select br from BuildRequest br where br.customer.id = ?1")
    List<BuildRequest> getBuildRequestsByCustomerId(Integer customerId);

    @Query("select br from BuildRequest br where br.land.id = ?1")
    List<BuildRequest> getBuildRequestsByLandId(Integer landId);

}