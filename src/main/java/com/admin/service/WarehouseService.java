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
    public Map<String, Object> listWarehouse(String keyword) {
        Integer count = warehouseMapper.count(keyword);
        List<Warehouse> warehouseList = warehouseMapper.listByName(keyword);
        warehouseList.forEach(warehouse -> {
            List<Product> productList = warehouseProductMapper.listProduct(warehouse.getId());
            warehouse.setProductList(productList);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("warehouseList", warehouseList);
        return map;
    }

    @Transactional
	public int insertWarehouse(Warehouse warehouse) throws Exception {
        Warehouse temp = warehouseMapper.selectByName(warehouse.getName());
		if (temp != null) {
			throw new Exception("仓库名称重复");
		}
        int res = warehouseMapper.insert(warehouse);
        if (res != 1) {
            throw new Exception("仓库插入失败");
        }
        return 1;
    }


	@Transactional
	public int updateWarehouse(Warehouse warehouse) throws Exception  {
		int res = warehouseMapper.update(warehouse);
        if (res != 1) {
			throw new Exception("修改仓库信息失败");
		}
        return 1;
    }

//    @Transactional
//	public int deleteWarehouseById(int id) {
//        int res = warehouseMapper.deleteWarehouseById(id);
//        // TODO 这里需要级联删除掉其他的表中对应项
//        // 但有些表的Mapper都没有，所以先暂时放着，目前请勿调用这个API
//        // 包括tb_employee_warehouse, tb_warehouse_product, tb_order 的所有对应项
//        // 对 tb_order 需进行伪删除？
//
//        if (res != 1) {
//            throw new DeleteException("删除仓库时发生异常");
//        }
//        else
//            return 1;
//    }
}
