import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from "@/views/Login.vue"
import Home from '@/views/Home.vue'
import Warehouse from '@/components/Warehouse.vue'
import Inventory from '@/components/product/Inventory.vue'
import Stock from '@/components/product/Stock.vue'
import Chart from '@/components/product/Chart.vue'
import User from '@/components/hr/User.vue'
import Role from '@/components/hr/Role.vue'
import Employee from '@/components/hr/Employee.vue'

Vue.use(VueRouter);

export default new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			redirect: '/login',
		},
		{
			path: '/login',
			component: Login
		},
		{
			path: '/home',
			component: Home,
			meta: { requireAuth: true },
			children: [
				{
					path: 'warehouse',
					name: "仓库信息",
					component: Warehouse,
					meta: { requireAuth: true }
				},
				{
					path: 'inventory',
					name: "产品清单",
					component: Inventory,
					meta: { requireAuth: true }
				},
				{
					path: 'stock-io',
					name: "进销记录",
					component: Stock,
					meta: { requireAuth: true }
				},
				{
					path: 'employee',
					name: "员工管理",
					component: Employee,
					meta: { requireAuth: true }
				},
				{
					path: 'user',
					name: "用户管理",
					component: User,
					meta: { requireAuth: true }
				},
				{
					path: 'role',
					name: "角色管理",
					component: Role,
					meta: { requireAuth: true }
				},
				{
					path: 'chart',
					name: "数据统计",
					component: Chart,
					meta: { requireAuth: true }
				}
			]
		}
	]
});