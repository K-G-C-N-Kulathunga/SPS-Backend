error id: file:///C:/Users/NUWAN%20CHANUKA/OneDrive/Documents/GitHub/SPS-Backend/Backend/sps/src/main/java/com/it/sps/controller/PivCostItemController.java:org/springframework/web/bind/annotation/RequestMapping#
file:///C:/Users/NUWAN%20CHANUKA/OneDrive/Documents/GitHub/SPS-Backend/Backend/sps/src/main/java/com/it/sps/controller/PivCostItemController.java
empty definition using pc, found symbol in pc: org/springframework/web/bind/annotation/RequestMapping#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 269
uri: file:///C:/Users/NUWAN%20CHANUKA/OneDrive/Documents/GitHub/SPS-Backend/Backend/sps/src/main/java/com/it/sps/controller/PivCostItemController.java
text:
```scala
package com.it.sps.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.@@RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.sps.entity.PivCostItem;
import com.it.sps.service.PivCostItemService;

@RestController

@RequestMapping("/piv-cost-item")
public class PivCostItemController {

    @Autowired
    private PivCostItemService service;

    @PostMapping
    public PivCostItem create(@RequestBody PivCostItem item) {
        return service.save(item);
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: org/springframework/web/bind/annotation/RequestMapping#