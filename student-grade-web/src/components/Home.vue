<script  setup>
import {
    CaretBottom,
    User,
    Crop,
    EditPen,
    SwitchButton,
    UserFilled,
    Document,
    Menu as IconMenu,
    Location,
    Setting
} from '@element-plus/icons-vue'

import { ref } from "vue"
import Aside from "@/components/Aside.vue"
import Header from "@/components/Header.vue"
import { loadMenuServer } from "@/api/menuApi"
import dynamicRouter from "@/store/routerStore.js"

const useDynamicRouter=dynamicRouter();//动态路由
//获取用户信息

const tabs = ref([])
// let routeList=[];
// 加载菜单的MenuList
const loadMenu = async () => {
    const result = await loadMenuServer();
    //  加载动态路由
    const menuList = result.data;

    useDynamicRouter.removeRouter();
    loadDynamicRouter(menuList);

    //  加载动态菜单
    tabs.value = result.data
    console.log("result.data=",result.data);
}
//加载动态路由
const loadDynamicRouter = (menuList) => {
    menuList.forEach(menuItem => {
        // console.log('menuItem=',menuItem);
        if (menuItem.menuList && menuItem.menuList.length > 0) {
            loadDynamicRouter(menuItem.menuList);
        }
        if (menuItem.menuType === 1) {
            const childRouter = {
                path: menuItem.path,
                componentName:menuItem.component,
                component: () => import('../components/view/' + menuItem.component + ".vue")
            }
            // router.addRoute("home", childRouter);
            useDynamicRouter.pushRouter(childRouter);
        }
    })
}
loadMenu();

// ================头像子传父==============
const childHeader=ref(null)
//父组件接受
const callChidHeaderMethod=(avatar)=>{
    childHeader.value.testLoad(avatar)
}


</script>

<template>
    <el-container class="home-container">
        <!-- 头部区 -->
        <el-header>
            <!-- ref="childHeader"就是将Header这个对象在js中什么，以便调用其中的方法 -->
            <Header ref="childHeader"/>
        </el-header>
        <!-- 页面主体区域 -->
        <el-container>
            <!-- 侧边栏 -->
            <el-aside style="height: 100vh;width: 200px;">
                <el-menu active-text-color="#ffd04b" background-color="#545c64" default-active="2" text-color="#fff"
                    @open="handleOpen" @close="handleClose" router>
                    <Aside :tabs="tabs" />
                </el-menu>
            </el-aside>
            <!-- 右侧内容主体 -->
            <el-main>
                <!-- @fatherMethod="callChidHeaderMethod"的作用仅仅在修改头像的时候用到-->
                <router-view @fatherMethod="callChidHeaderMethod"></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>
   
<style lang="less" scoped>
.home-container {
    height: 100%;
}

.el-header {
    background-color: #373d41;
    display: flex; //设置显示为flex布局
    justify-content: space-between; //设置为flex左右布局
    padding-left: 0; //左内边距为0（Logo贴左边）
    align-items: center; //元素上下居中（防止右边按钮贴上下边）
    color: #fff;
    font-size: 20px;

    >div {
        //内嵌的div样式
        display: flex;
        align-items: center; //Logo和文字上下居中

        span {
            margin-left: 15px; //文字左侧设置间距，防止与Logo紧贴
        }
    }
}

.el-main {
    background-color: #eaedf1;
    width: 100%;
    height: 100vh;
}

.el-aside {
    background-color: #333744;
}
</style>