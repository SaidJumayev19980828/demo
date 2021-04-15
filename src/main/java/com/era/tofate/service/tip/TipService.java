package com.era.tofate.service.tip;

import com.era.tofate.entities.tip.Tip;
import com.era.tofate.service.GeneralService;
import java.util.List;

public interface TipService extends GeneralService<Tip> {
    List<Tip> findAllByOrderBySequenceNumberAsc();
}
