package com.it.sps.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.it.sps.dto.ApplicationLoanTypeDropDownDTO;
import com.it.sps.entity.SpLoanSM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationLoanTypeRepository extends JpaRepository<SpLoanSM, String> {

    @Query("SELECT new com.it.sps.dto.ApplicationLoanTypeDropDownDTO(a.loanCode, a.loanName)" +
            "FROM SpLoanSM a " +
            "WHERE a.status = '1'" +
            "ORDER BY a.sortKey DESC")
    List<ApplicationLoanTypeDropDownDTO> findActiveLoanTypes();
}