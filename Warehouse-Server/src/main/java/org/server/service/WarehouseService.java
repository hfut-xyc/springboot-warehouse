package org.server.service;

import org.server.mapper.WarehouseMapper;
import org.server.entity.Warehouse;
import org.server.exception.RepeatException;
import org.server.exception.InsertException;
import org.server.exception.DeleteException;

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

	public int getWarehouseCount(String keyword) {
        return warehouseMapper.getWarehouseCount(keyword);
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

	public int deleteWarehouseById(int id) {
        int res = warehouseMapper.deleteWarehouseById(id);
        if(res != 1) {
            throw new DeleteException("删除仓库时发生异常");
        }
        else
            return 1;
    }
}