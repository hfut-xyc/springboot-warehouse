package com.admin.service;

import com.admin.entity.Product;
import com.admin.entity.Warehouse;
import com.admin.mapper.WarehouseMapper;
import com.admin.mapper.WarehouseProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;

    @Resource
    private WarehouseProductMapper warehouseProductMapper;

    /**
     * 根据仓库名分页查询仓库以及所含的产品
     * @param keyword
     * @return
     */
    public Map<String, Object> selectListByName(String keyword) {
        Integer count = warehouseMapper.count(keyword);
        List<Warehouse> warehouseList = warehouseMapper.selectListByName(keyword);
        warehouseList.forEach(warehouse -> {
            List<Product> productList = warehouseProductMapper.listProduct(warehouse.getId());
            warehouse.setProductList(productList);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("warehouseList", warehouseList);
        return map;
    }

    /**
     * 添加仓库
     * @param warehouse
     * @return
     * @throws Exception
     */
    @Transactional
	public Integer insert(Warehouse warehouse) throws Exception {
        Warehouse temp = warehouseMapper.selectByName(warehouse.getName());
		if (temp != null) {
			throw new Exception("仓库名称重复");
		}
        Integer res = warehouseMapper.insert(warehouse);
        if (res != 1) {
            throw new Exception("添加仓库失败");
        }
        return 1;
    }


	@Transactional
	public Integer update(Warehouse warehouse) throws Exception  {
		Integer res = warehouseMapper.update(warehouse);
        if (res != 1) {
			throw new Exception("修改仓库失败");
		}
        return 1;
    }


    @Transactional
    public Integer deleteById(Integer id) throws Exception  {
        Integer res = warehouseMapper.deleteById(id);
        if (res != 1) {
            throw new Exception("删除仓库失败");
        }
        return res;
    }

}
