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

	// 添加新仓库
	int addWarehouse(Warehouse warehouse);

	// 修改仓库基本信息
	int updateWarehouseInfo(Warehouse warehouse);

	// 按仓库id为该仓库添加员工
	int addEmployeeByWid(int wid, List<Integer> eidList);

	// 按仓库id删除管理该仓库的所有员工
	int deleteAllEmployeeByWid(int wid);

	// 通过仓库id和产品id查询该仓库中对应产品的库存
	Integer getProductStockByWid(@Param("wid") int wid, @Param("pid") int pid);

	// 处理旧产品的订单时，更新原有库存记录
	int updateProductByWid(@Param("wid") int wid, @Param("pid") int pid, @Param("amount") int amount);

	// 处理新产品的订单时，添加一条库存记录
	int addProductByWid(@Param("wid") int wid, @Param("pid") int pid, @Param("amount") int amount);
}
