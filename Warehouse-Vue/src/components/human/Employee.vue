<template>
  <el-container>
    <el-header style="margin-top: 20px;">
      <el-row :gutter="12">
        <el-col :span="8">
          <el-input v-model="keyword" placeholder="通过员工姓名搜索" prefix-icon="el-icon-search"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button @click="searchEmployee()" type="primary" icon="el-icon-search">查询</el-button>
        </el-col>
        <el-col :span="3">
          <el-button @click="isAddDialogVisible=true" type="success" icon="el-icon-plus" plain>添加新员工</el-button>
        </el-col>
      </el-row>
    </el-header>

    <el-table :data="employeeList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="工号" sortable fixed width="100"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100"></el-table-column>
      <el-table-column prop="gender" label="性别" width="50"></el-table-column>
      <el-table-column prop="phone" label="联系电话" width="130"></el-table-column>
      <el-table-column prop="salary" label="薪水" width="130"></el-table-column>
      <el-table-column prop="birthday" label="出生日期" sortable width="120"></el-table-column>
      <el-table-column prop="hireDate" label="入职日期" sortable width="120"></el-table-column>
      <el-table-column prop="warehouse" label="仓库管辖" width="500">
        <template slot-scope="scope">
          <el-tooltip placement="top" v-for="item in scope.row.warehouses" :key="item.name">
            <div slot="content">
              仓库编号：{{item.id}}<br/>
              名称：{{item.name}}<br/>
            </div>
            <el-tag>{{item.name}}</el-tag>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template slot-scope="scope">
          <el-button @click="showEditDialog(scope.row)" size="mini" icon="el-icon-edit" type="warning" plain>编辑
          </el-button>
          <el-button @click="deleteEmployee(scope.row)" size="mini" icon="el-icon-delete" type="danger" plain>删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-footer>
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        background
        style="margin-top: 10px;"
        :total="total"
        :current-page="page"
        :page-size="pageSize"
        @current-change="onPageChange"
        @size-change="onPageSizeChange"
        :page-sizes="[5, 10, 15, 20, 25]"
      ></el-pagination>
    </el-footer>
    <!--添加员工对话框-->
    <el-dialog title="添加新员工" :visible.sync="isAddDialogVisible">
      <el-form ref="addForm" :model="addForm" :rules="rules" status-icon label-width="120px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="addForm.name" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="addForm.phone" prefix-icon="el-icon-phone"></el-input>
        </el-form-item>
        <el-form-item label="薪水" prop="salary">
          <el-input v-model="addForm.salary" prefix-icon="el-icon-money"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addForm.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-col :span="8">
            <el-date-picker v-model="addForm.birthday" :editable="false" placeholder="选择日期"></el-date-picker>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addEmployee()" type="primary">添加</el-button>
        <el-button @click="isAddDialogVisible=false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑员工对话框-->
    <el-dialog title="编辑员工信息" :visible.sync="isEditDialogVisible">
      <el-collapse accordion>
        <el-collapse-item>
          <template slot="title">
            <i class="el-icon-info"></i>基本信息
          </template>
          <el-form ref="editForm" :model="editForm" :rules="rules" status-icon label-width="120px">
            <el-form-item label="工号" prop="id">
              <el-input v-model="editForm.id" prefix-icon="el-icon-user" disabled></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="editForm.name" prefix-icon="el-icon-user"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="editForm.phone" prefix-icon="el-icon-phone"></el-input>
            </el-form-item>
            <el-form-item label="薪水" prop="salary">
              <el-input v-model="editForm.salary" prefix-icon="el-icon-money"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="editForm.gender">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="出生日期">
              <el-col :span="8">
                <el-date-picker v-model="editForm.birthday" :editable="false" placeholder="选择日期"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="入职日期">
              <el-col :span="8">
                <el-date-picker v-model="editForm.hireDate" disabled></el-date-picker>
              </el-col>
            </el-form-item>
          </el-form>
          <div style="float: right; margin-top: 15px; margin-bottom: 10px">
            <el-button @click="updateEmployee" type="primary">修改基本信息</el-button>
            <el-button @click="isEditDialogVisible=false">取消</el-button>
          </div>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <i class="el-icon-s-home"></i>仓库信息
          </template>
          <el-transfer :titles="['未分配仓库', '已分配仓库']" :data="transferLeft" v-model="transferRight" filterable></el-transfer>
          <div style="float: right; margin-top: 15px; margin-bottom: 10px">
            <el-button @click="updateWarehouse" type="primary">修改仓库信息</el-button>
            <el-button @click="isEditDialogVisible=false">取消</el-button>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>
  </el-container>
</template>

<script>
  export default {
    name: "Employee",
    data() {
      return {
        total: 0, // 查询到的用户总数
        page: 1, // 当前页码
        pageSize: 10, // 当前页面大小
        keyword: "", // 查询用户名的关键字
        loading: true,
        employeeList: [],
        isAddDialogVisible: false,   // 添加员工的对话框是否可见
        isEditDialogVisible: false,  // 编辑员工的对话框是否可见
        transferLeft: [],
        transferRight: [],
        addForm: {      // 添加员工表单
          name: "",
          gender: "",
          phone: "",
          birthday: "",
          salary: ""
        },
        editForm: {},   // 编辑员工表单
        rules: {
          name: [{required: true, message: "姓名不能为空", trigger: "blur"}],
          phone: [{required: true, message: "电话不能为空", trigger: "blur"}],
          salary: [{required: true, message: "薪水不能为空", trigger: "blur"}]
        }
      }
    },

    mounted() {
      this.loading = true;
      this.loadEmployeeList("/employees");
    },

    methods: {
      loadEmployeeList(url) {
        let that = this;
        this.$axios.get(url).then(res => {
          if (res.status === 200) {
            console.log(res);
            that.employeeList = res.data.employeeList;
            that.total = res.data.total;
            that.loading = false;
            that.$message.success("数据加载成功");
          } else {
            that.loading = false;
            that.$message.error("数据加载失败");
          }
        }).catch(err => {
          that.loading = false;
          that.$message.error("服务器异常");
        });
      },

      reloadEmployeeList() {
        this.loadEmployeeList(`/employees?page=${this.page}&pageSize=${this.pageSize}`);
      },

      searchEmployee() {
        if (this.keyword.trim() === "") {
          this.$message.warning("请输入关键字");
          return;
        }
        this.loading = true;
        let url = "/employees?page=" + this.page + "&pageSize=" + this.pageSize + "&keyword=" + this.keyword.trim();
        this.loadEmployeeList(url);
      },

      onPageChange(val) {
        this.page = val;
        this.loading = true;
        let url = "/employees?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadEmployeeList(url);
      },

      onPageSizeChange(val) {
        this.pageSize = val;
        if (this.pageSize * (this.page - 1) >= this.total) {
          this.page = 1;
          this.pageSize = 10;
        }
        this.loading = true;
        let url = "/employees?page=" + this.page + "&pageSize=" + this.pageSize;
        if (this.keyword !== "") {
          url += "&keyword=" + this.keyword.trim();
        }
        this.loadEmployeeList(url);
      },

      addEmployee() {
        this.$refs["addForm"].validate(valid => {
          if (valid) {
            var that = this;
            this.$axios.post("/employee/add", this.addForm).then(res => {
              if (res.status === 200) {
                if (res.data === 1) {
                  that.$message.success("员工添加成功");
                  that.isAddDialogVisible = false;
                  that.addForm = {name: "", phone: "", salary: ""};
                  that.reloadEmployeeList();
                } else {
                  that.$message.warning("员工添加失败");
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

      showEditDialog(row) {
        let left = [];
        let right = [];
        // 加载该员工未管理的仓库
        this.$axios.get("/warehouses?page=1&pageSize=10").then(res => {
          res.data.warehouseList.forEach((item, index) => {
            left.push({
              label: item.name,
              key: item.id,
              disabled: false
            });
          });
        });
        // 加载该员工已经管理的仓库
        row.warehouses.forEach(item => {
          right.push(item.id);
        });
        this.transferLeft = left;
        this.transferRight = right;
        this.editForm = row;
        this.isEditDialogVisible = true;
      },

      updateEmployee() {
        this.$refs.editForm.validate(valid => {
          if (valid) {
            var that = this;
            this.$axios.post(`/employee/${this.editForm.id}/update/info`, this.editForm)
            .then(res => {
              if (res.status === 200) {
                if (res.data === 1) {
                  that.$message.success("员工基本信息修改成功");
                  reloadEmployeeList();
                } else {
                  that.$message.warning("员工基本信息修改失败");
                }
              } else if (res.status === 403) {
                that.$message.warning("权限不足，请联系管理员");
              }
              that.isEditDialogVisible = false;
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

      updateWarehouse() {
        var that = this;
        this.$axios.post(`/employee/${this.editForm.id}/update/warehouse`, {"widList":this.transferRight})
        .then(res => {
          if (res.status === 200) {
            if (res.data === 1) {
              that.$message.success("员工管辖仓库修改成功");
              that.reloadEmployeeList();
            } else {
              that.$message.warning("员工管辖仓库修改失败");
            }
          } else if (res.status === 403) {
            that.$message.warning("权限不足，请联系管理员");
          }
          this.isEditDialogVisible = false;
        })
        .catch(err => {
          console.log(err);
          that.$message.error("服务器异常");
        });
      },

      deleteEmployee(row) {
        let that = this;
        this.$axios.delete(`/employee/${row.id}/delete`).then(res => {
          if (res.status === 200) {
            if (res.data === 1) {
              that.$message.success("员工删除成功");
              that.reloadEmployeeList();
            } else {
              that.$message.warning("员工删除失败");
            }
          } else if (res.status === 403) {
            that.$message.warning("权限不足，请联系管理员");
          }
        });
      }
    }
  };
</script>

<style>
</style>