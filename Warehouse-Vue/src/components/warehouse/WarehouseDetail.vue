<template>
  <el-container>
    <el-card class="el-card-detail">
      <div slot="header">
        <el-page-header @back="goBack" content="详情页面">
        </el-page-header>
      </div>
      <el-divider content-position="left">基本信息</el-divider>
      <div>
        <el-form ref="updateForm" :model="updateForm" :rules="rules" status-icon label-width="100px">
        <el-form-item label="仓库编号" prop="id">
          <el-input v-model="updateForm.id" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="仓库名称" prop="name">
          <el-input v-model="updateForm.name" :disabled="false"></el-input>
        <el-button v-if="updateForm.name != info.name" icon="el-icon-edit" @click="updateWarehouseInfo()" type="primary">保存修改</el-button>
        </el-form-item>
        </el-form>
      </div>
      <el-divider content-position="left">仓库负责人
      </el-divider>
      <template v-if="!isEditingEmployees">
        <el-tooltip placement="top" v-for="operator in info.operators" :key="operator.name">
          <div slot="content">
            工号：{{operator.id}}<br/>
            姓名：{{operator.name}}<br/>
            性别：{{operator.gender}}<br/>
            电话号码：{{operator.phone}}
          </div>
          <el-tag>{{operator.name}}</el-tag>
        </el-tooltip>
        <el-button icon="el-icon-edit" size="small" @click="editEmployeeTag()">编辑</el-button>
      </template>
      <template v-else>
        <el-tooltip placement="top" v-for="operator in info.operators" :key="operator.name">
          <div slot="content">
            工号：{{operator.id}}<br/>
            姓名：{{operator.name}}<br/>
            性别：{{operator.gender}}<br/>
            电话号码：{{operator.phone}}<br/>
          </div>
          <el-tag closable @close="deleteEmployeeTag(operator.id)">{{operator.name}}</el-tag>
        </el-tooltip>
        <span class="el-button-like">
        <el-popover
          placement="right"
          width="400"
          trigger="click">
          <el-table :data="employeeList" ref="employeeTable" @selection-change="handleEmployeeListSelction">
            <el-table-column type="selection"></el-table-column>
            <el-table-column property="id" label="工号"></el-table-column>
            <el-table-column property="name" label="姓名"></el-table-column>
          </el-table>
          <el-button slot="reference" icon="el-icon-plus" size="small" type="primary" @click="updateEmployeeListSelction()">添加员工</el-button>
        </el-popover>
        </span>
        <el-button icon="el-icon-check" size="small" @click="updateWarehouseEmployee()" type="success">确定</el-button>
        <p class="edit-tip">单击<i class="el-icon-close"></i>以移除该员工和仓库的管理关系</p>
      </template>

      <el-divider content-position="left">仓库存储清单</el-divider>
      <el-table :data="productList" v-loading="loading" border stripe>
        <el-table-column prop="id" label="产品编号" sortable width="150"></el-table-column>
        <el-table-column prop="name" label="产品名"></el-table-column>
        <el-table-column prop="supplier" label="供应商"></el-table-column>
        <el-table-column prop="total" label="数量" sortable></el-table-column>
      </el-table>
    </el-card>
  </el-container>
</template>

<script>
  export default {
    name: "WarehouseDetail",
    data: function () {
      const warehouseNameValidator = (rule, value, callback) => {
        if (value === '')
          callback(new Error('名称不能为空'))
        else
          callback()
      };
      return {
        info: {},
        productList: [],
        updateForm: {
          id: 0,
          name: ""
        },
        rules: {
          name: [{validator: warehouseNameValidator, trigger: "blur"}]
        },
        loading: false,
        employeeList: [],
        employeeListSelection: [],
        isEditingEmployees: false,
      }
    },

    mounted() {
      this.info = this.$route.params;
      this.updateForm.id = this.info.id;
      this.updateForm.name = this.info.name;
      this.loadProductList("/warehouse/" + this.info.id + "/products");
    },

    methods: {
      goBack() {
        this.$router.replace("/home/warehouse");
      },

      loadProductList(url) {
        this.loading = true;
        var that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.productList = res.data.productList;
            that.loading = false;
            that.$message.success("数据加载成功");
          } else {
            that.loading = false;
            that.$message.error("数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.loading = false;
          that.$message.error("服务器异常");
        });
      },

      loadEmployeeList(url) {
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.employeeList = res.data.employeeList;
            that.employeeList.forEach(v => v.warehouses = null);
          } else {
            that.$message.error("数据加载失败");
          }
        }).catch(res => {
          console.log(res);
          that.$message.error("服务器异常");
        });
      },

      updateEmployeeListSelction() {
        this.info.operators.forEach((employee, index, array) => {
          this.employeeList.find((v, i, obj) => {
            if(v.id == employee.id) {
              // this.$refs.employeeTable.toggleRowSelection(employee, true);
              this.employeeList.splice(i, 1);
              return true;
            }
          });
        });
      },

      handleEmployeeListSelction(vals) {
        this.info.operators = this.info.operators.concat(vals);
        vals.forEach((employee, index, array) => {
          console.log(vals);
          this.employeeList.find((v, i, obj) => {
            if(v.id == employee.id) {
              this.employeeList.splice(i, 1);
              return true;
            }
          });
        });
        this.$refs.employeeTable.clearSelection();
      },

      // 商品分页，Required!
      onPageChange(val) {
        this.productLoading = true;
        let url = "/warehouse/" + this.info.id + "/products?page=" + val;
        this.loadProductList(url);
      },

      updateWarehouseInfo() {
        this.$refs["updateForm"].validate(valid => {
          if (valid) {
            var that = this;
            this.$axios.post("/warehouse/update/info", {
              id: this.updateForm.id,
              name: this.updateForm.name,
            })
            .then(res => {
              if (res.status === 200) {
                if (res.data === 1) {
                  that.$message.success("仓库名称修改成功");
                  this.info.name = this.updateForm.name;
                } else {
                  that.$message.warning("仓库名称修改失败");
                }
              } else if (res.status === 403) {
                that.$message.warning("权限不足，请联系管理员");
              }
            })
            .catch(err => {
              console.log(err);
              that.$message.error("服务器异常");
            });
          } else {
            return false;
          }
        });
      },

      editEmployeeTag() {
        this.isEditingEmployees = true;
        this.loadEmployeeList("/employees");
      },

      deleteEmployeeTag(eid) {
        let i = -1;
        this.info.operators.find((value, index, obj) => { if(value.id == eid) {i=index;return true;} });
        if (i!= -1) {
          this.info.operators.splice(i, 1);
        }
      },

      updateWarehouseEmployee() {
        let that = this;
        let eidList = [];
        this.info.operators.forEach(v => eidList.push(v.id)); 
        this.$axios.post(`/warehouse/${this.info.id}/employees`, eidList)
        .then(res => {
          if (res.status === 200) {
            if (res.data === 1) {
              that.$message.success("仓库负责人修改成功");
              this.isEditingEmployees = false;
            } else {
              that.$message.warning("仓库负责人修改失败");
            }
          } else if (res.status === 403) {
            that.$message.warning("权限不足，请联系管理员");
          }
        })
        .catch(err => {
          console.log(err);
          that.$message.error("服务器异常");
        });
      },
    }
  }
</script>

<style>
.el-tag + .el-button, .el-input + .el-button, 
.el-tag + .el-button-like, .el-button-like + .el-button {
  margin-left: 10px;
}

.transfer-footer {
  text-align: center;
}

.transfer {
  text-align: center;
  max-width: 700px;
}

.el-input {
  max-width: 400px;
}

.el-card-detail {
  margin-top: 15px;
  width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.edit-tip {
  color: gray;
  font-size: small;
}
</style>