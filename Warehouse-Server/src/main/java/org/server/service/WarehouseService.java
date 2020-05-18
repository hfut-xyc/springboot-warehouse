package org.server.service;

import org.server.mapper.WarehouseMapper;
import org.server.entity.Product;
import org.server.entity.Warehouse;
import org.server.exception.*;

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

	public List<Product> getProductListById(int id) {
        Warehouse temp = warehouseMapper.getWarehouseById(id);
		if (temp == null) {
            throw new NotFoundException("仓库ID不存在");
		} else {
            return warehouseMapper.getProductListById(id);
        }
    }

    @Transactional
	public int addWarehouse(Warehouse warehouse) {
        Warehouse temp = warehouseMapper.getWarehouseByName(warehouse.getName());
		if (temp != null) {
			throw new RepeatException("仓库名称重复");
		}
        int res = warehouseMapper.addWarehouse(warehouse);
        if (res != 1) {
            throw new InsertException("仓库插入失败");
        }
        return 1;
    }


	@Transactional
	public int updateWarehouse(Warehouse warehouse) {
		if (warehouseMapper.updateWarehouse(warehouse) == 1) {
			return 1;
		} else {
			throw new UpdateException("修改仓库信息失败");
		}
    }
    
    @Transactional
    public int updateEmployeeByWid(int wid, List<Integer> eidList) {
		warehouseMapper.deleteAllEmployeeByWid(wid);
		int res = warehouseMapper.addEmployeeByWid(wid, eidList);
		if (eidList.size() != res) {
			throw new UpdateException("未能成功修改员工负责的仓库");
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
