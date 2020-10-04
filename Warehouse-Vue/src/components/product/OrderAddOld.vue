<template>
  <el-container style="text-align: center; width: 400px; margin-left: auto; margin-right: auto;">
    <el-main>
      <el-form ref="addOrderForm" :model="addOrderForm" :rules="rules" label-width="100px">
        <el-form-item label="仓库名称" prop="wid">
          <el-select v-model="addOrderForm.wid" @change="addOrderForm.eid = ''" :loading="warehouseLoading" filterable>
            <el-option
              v-for="warehouse in warehouseList"
              :key="warehouse.id"
              :label="warehouse.name"
              :value="warehouse.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="pid">
          <el-select v-model="addOrderForm.pid" :loading="productLoading" filterable>
            <el-option
              v-for="product in productList"
              :key="product.id"
              :label="product.name"
              :value="product.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="操作员工" prop="eid">
          <template v-if="addOrderForm.wid !== ''">
            <el-select v-model="addOrderForm.eid" :loading="warehouseLoading" filterable>
              <!-- 在v-for属性中唯有find才能正确取出对应的operators -->
              <el-option
                v-for="operator in warehouseList.find(item => item.id === addOrderForm.wid).operators"
                :key="operator.id"
                :label="operator.name"
                :value="operator.id">
              </el-option>
            </el-select>
          </template>
          <template v-else>
            <el-select v-model="addOrderForm.eid" disabled placeholder="请先选择仓库">
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="产品数量" prop="amount">
          <el-input-number v-model.number="addOrderForm.amount" step-strictly controls-position="right"
                           :min="getProductMin()">
          </el-input-number>
        </el-form-item>
        <el-form-item label="产品报废">
          <el-checkbox v-model="isObsolete" :disabled="addOrderForm.amount >= 0">是否为报废产品</el-checkbox>
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer>
      <el-button @click="addOrderOld()" type="primary">添加</el-button>
      <el-button @click="resetAddOrderForm()">重置</el-button>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: "OrderAddOld",
    data() {
      const amountValidator = (rule, value, callback) => {
        if (value === 0)
          callback(new Error('商品数量不能为0'))
        else
          callback()
      };
      return {
        addOrderForm: {
          wid: '',
          eid: '',
          pid: '',
          amount: 0,
          status: '正常',
        },
        rules: {
          wid: [{required: true, message: "仓库名称不能为空", trigger: "blur"}],
          eid: [{required: true, message: "操作员工不能为空", trigger: "blur"}],
          pid: [{required: true, message: "商品名称不能为空", trigger: "blur"}],
          amount: [{required: true, validator: amountValidator, trigger: 'change'}],
        },
        productList: [],          // 产品列表，此处包含了目前已经有的产品及数量
        warehouseList: [],        // 员工列表，这里包括了仓库的员工。
        productLoading: false,
        warehouseLoading: false,
        isObsolete: false
      }
    },

    mounted() {
      this.loadProductList('/products?pageSize=100');
      this.loadWarehouseList('/warehouses?pageSize=100');
    },

    methods: {
      loadProductList(url) {
        this.productLoading = true;
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.productList = res.data.productList;
            that.productLoading = false;
          } else {
            that.productLoading = false;
            that.$message.error("产品数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.productLoading = false;
          that.$message.error("服务器异常");
        });
      },

      loadWarehouseList(url) {
        this.warehouseLoading = true;
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.warehouseList = res.data.warehouseList;
            that.warehouseLoading = false;
          } else {
            that.warehouseLoading = false;
            that.$message.error("仓库数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.warehouseLoading = false;
          that.$message.error("服务器异常");
        });
      },

      // 此为物品总量(total)对应的最小值
      getProductMin() {
        let p = this.productList.find(i => i.id === this.addOrderForm.pid);
        if (p === undefined)
          return -NaN;
        else
          return -p.total;
      },

      addOrderOld() {
        this.$refs.addOrderForm.validate(valid => {
          if (valid) {
            var that = this;
            this.$axios.post("/order/add/old", this.addOrderForm).then(res => {
              if (res.status === 200) {
                if (res.data === 1) {
                  that.$message.success("订单添加成功");
                  that.resetAddOrderForm();
                } else {
                  that.$message.warning("订单添加失败");
                }
              } else if (res.status === 403) {
                that.$message.warning("权限不足，请联系管理员");
              }
            }).catch(err => {
              console.log(err);
              that.$message.error("服务器异常");
            });
          } else {
            return false;
          }
        });
      },

      resetAddOrderForm() {
        this.addOrderForm = {
          wid: '',
          eid: '',
          pid: '',
          amount: 0,
          status: '正常',
        };
      }
    }
  }
</script>

<style scoped>

</style>