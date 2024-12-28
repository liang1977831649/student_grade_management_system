<script setup>
import {Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  User}  from '@element-plus/icons-vue'
import {computed} from "vue"
import userInfoServer from "@/store/useInfo"

const useUserInfo=userInfoServer();
const formatdata=computed(()=>{
  const oDate=new Date()
  const year=oDate.getFullYear()
  const month=oDate.getMonth()+1
  const day=oDate.getDate()
  const hour=oDate.getHours()
  const min=oDate.getMinutes();
  const second=oDate.getSeconds();
  return year+'-'+month+'-'+day+' '+hour+':'+min+':'+second
})
</script>

<template>
    <div>
  <el-descriptions
    class="margin-top"
    title="用户基本信息"
    column="3"
    border
  >
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <user />
          </el-icon>
          学生姓名
        </div>
      </template>
      {{useUserInfo.userInfo.name}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <iphone />
          </el-icon>
          手机号码
        </div>
      </template>
      {{useUserInfo.userInfo.phone}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <location />
          </el-icon>
          用户性别
        </div>
      </template>
      <el-tag :type="useUserInfo.userInfo.sex==1?'success':'primary'">
        {{ useUserInfo.userInfo.sex==1?'男':'女' }}
      </el-tag>
    </el-descriptions-item>

    <!--:column="3"，因此，个数到达3个就自动分行  -->
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <tickets />
          </el-icon>
          出生年月
        </div>
      </template>
      {{useUserInfo.userInfo.birthday}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <office-building />
          </el-icon>
          学生学号
        </div>
      </template>
      {{useUserInfo.userInfo.id}}
    </el-descriptions-item>

    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <office-building />
          </el-icon>
          学生身份
        </div>
      </template>
      <el-tag :type="useUserInfo.userInfo.role==0?'success':'primary'">
        {{useUserInfo.userInfo.role==0?'管理员':'学生'}}
      </el-tag>
    </el-descriptions-item>
  </el-descriptions>
  <h1 style="margin-left:26%;font-size: 36px;margin-top: 5%">{{'当地时间：'+formatdata }}</h1>
</div>
</template>


<style scoped>
.el-descriptions {
  margin-top: 20px;
}
.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}
</style>
