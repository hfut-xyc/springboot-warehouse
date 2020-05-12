package org.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.entity.Product;
import org.server.entity.Warehouse;

@Mapper
public interface WarehouseMapper {

	List<Warehouse> getWarehouseList(@Param("keyword") String keyword);

	List<Product> getProductListById(int id);

	Warehouse getWarehouseById(int id);

	Warehouse getWarehouseByName(String name);

	int addWarehouse(Warehouse warehouse);

	// 处理新增产品的订单时，添加一条库存记录
	int addWarehouseProduct(@Param("wid") int wid, @Param("pid") int pid, @Param("amount") int amount);

	// 处理原有产品的订单时，更新原有库存记录
	int updateWarehouseProduct(@Param("wid") int wid, @Param("pid") int pid, @Param("amount") int amount);

	//	int deleteWarehouseById(int id);
}
