package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.server.pojo.entity.Product;
import org.server.pojo.entity.Warehouse;

import java.util.List;

@Mapper
public interface WarehouseMapper {

	List<Warehouse> getWarehouseList(@Param("keyword") String keyword);

	List<Product> getProductListById(int id);

	Warehouse getWarehouseByName(String name);

	Product getProductByWidAndPid(int wid, int pid);

	// 添加新仓库
	int addWarehouse(Warehouse warehouse);

	// 修改仓库基本信息
	int updateWarehouseInfo(Warehouse warehouse);

	// 处理旧产品的订单时，更新原有库存记录
	int updateProductByWid(@Param("wid") int wid, @Param("pid") int pid, @Param("amount") int amount);

	// 处理新产品的订单时，添加一条库存记录
	int addProductByWid(@Param("wid") int wid, @Param("pid") int pid, @Param("amount") int amount);
}
