package org.server.service;

import org.server.entity.Product;
import org.server.entity.Warehouse;
import org.server.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;

    public List<Warehouse> getWarehouseList(String keyword) {
        return warehouseMapper.getWarehouseList(keyword);
    }

	public List<? super Product> getProductListById(int id) throws Exception {
    	// 1.先从Redis中读取
//    	List<? super Product> products = redisTemplate.boundHashOps("warehouse:" + id).values();
//    	// 2.如果缓存中没有，再从MySQL中读取
//    	if (products == null || products.size() == 0) {
//    		products = warehouseMapper.getProductListById(id);
//		    if (products == null) {
//			    throw new Exception("仓库ID不存在");
//		    }
//		    // 3.将MySQL中的数据同步到Redis
//		    for (Object product : products) {
//			    redisTemplate.boundHashOps("warehouse:" + id).put("product:" + ((Product) product).getId(), product);
//		    }
//	    }
		List<? super Product> products = warehouseMapper.getProductListById(id);
		return products;
    }

    @Transactional
	public int addWarehouse(Warehouse warehouse) throws Exception {
        Warehouse temp = warehouseMapper.getWarehouseByName(warehouse.getName());
		if (temp != null) {
			throw new Exception("仓库名称重复");
		}
        int res = warehouseMapper.addWarehouse(warehouse);
        if (res != 1) {
            throw new Exception("仓库插入失败");
        }
        return 1;
    }


	@Transactional
	public int updateWarehouseInfo(Warehouse warehouse) throws Exception  {
		if (warehouseMapper.updateWarehouseInfo(warehouse) == 1) {
			return 1;
		} else {
			throw new Exception("修改仓库信息失败");
		}
    }
    
    @Transactional
    public int updateEmployeeByWid(int wid, List<Integer> eidList) throws Exception  {
		//warehouseMapper.deleteAllEmployeeByWid(wid);
		//if (eidList.size() == 0) {
		//	return 1;
		//}
		//int res = warehouseMapper.addEmployeeByWid(wid, eidList);
		//if (eidList.size() != res) {
		//	throw new Exception("未能成功修改员工负责的仓库");
		//}
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
