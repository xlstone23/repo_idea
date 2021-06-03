package com.lagou.service;

import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface PromotionSpaceService {
    /*
   获取所有广告位
    */
    public List<PromotionSpace> findAllPromotionSpace();
    /*
    添加&修改广告位
     */
    public void savePromotionSpace(PromotionSpace promotionSpace);
    public void updatePromotionSpace(PromotionSpace promotionSpace);
    /*
   根据Id查询广告位信息
    */
    public PromotionSpace findPromotionSpaceById(int id);
}
