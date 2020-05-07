<template>
  <el-container>
    <el-header style="background-color: #33b1f5;">
      <div class="title">仓库信息管理系统</div>

      <el-dropdown @command="logout" class="dropdown">
        <span class="userinfo">
          <el-avatar src=""></el-avatar>
          {{currentUsername}}
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <i class="el-icon-delete"></i>退出登录
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>

    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-openeds="['1', '2', '3']"
          style="background-color: rgb(216, 239, 255)"
          router
        >
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-home"></i>仓库管理
            </template>
            <el-menu-item index="/home/warehouse">
              <i class="el-icon-coin"></i>仓库信息
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>产品管理
            </template>
            <el-menu-item index="/home/inventory">
              <i class="el-icon-s-operation"></i>产品库存
            </el-menu-item>
            <el-menu-item index="/home/stock-io">
              <i class="el-icon-date"></i>进销记录
            </el-menu-item>
            <el-menu-item index="/home/chart">
              <i class="el-icon-s-data"></i>数据统计
            </el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-user-solid"></i>人力资源管理
            </template>
            <el-menu-item index="/home/employee">
              <i class="el-icon-edit-outline"></i>仓库员工管理
            </el-menu-item>
            <el-menu-item index="/home/user">
              <i class="el-icon-user"></i>用户管理
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item v-text="this.$route.name"></el-breadcrumb-item>
        </el-breadcrumb>
        <!-- show children components here-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      currentUsername: "admin"
    };
  },

  mounted() {
    // this.currentUsername = JSON.parse(
    //   window.sessionStorage.getItem("username")
    // );
    this.currentUsername = this.$store.state.username;
  },

  methods: {
    logout() {
      var _this = this;
      this.$axios
        .get("/logout")
        .then(res => {
          console.log(res);
          if (res.data === "success") {
            _this.$store.commit("logout");
            _this.$message.success("注销成功");
            _this.$router.replace("/login");
          }
        })
        .catch(error => {
          console.log(error);
          _this.$message.error("注销失败");
        });
    }
  }
};
</script>

<style>
.title {
  color: white;
  float: left;
  font-size: large;
  margin-top: 20px;
}

.dropdown {
  color: white;
  float: right;
  text-align: right;
  cursor: pointer;
  margin-top: 12px;
}

.userinfo {
  display: flex;
  align-items: center;
}
</style>