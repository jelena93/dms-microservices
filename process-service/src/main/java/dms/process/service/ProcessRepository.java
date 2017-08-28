/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.process.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Proces, Long> {

    List<Proces> findByParentIsNull();

    Process findByParent(Long parent);

    List<Proces> findByUser(String user);
}
