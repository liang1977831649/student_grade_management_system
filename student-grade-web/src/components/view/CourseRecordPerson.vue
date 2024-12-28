<script setup>
import {ref} from "vue"
import {getStudentCourseListPersonalServer,delCourseChooseServer} from "@/api/studentCourse"
import { ElMessage,ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue'

const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)

const handleCurrentChange = async (number) => {
  console.log("current page: ", number)
  searchBody.value.pageNum = number
  await searchButton()
}

const tableData=ref([
  {
    courseNo:'1001',
    courseName:'高等数学'
  },
  {
    courseNo:'1001',
    courseName:'高等数学'
  },
  {
    courseNo:'1001',
    courseName:'高等数学'
  }
])

const searchBody=ref({
  courseNo:'',
  pageNum:1,
  pageSize:''
})

const searchButton=async ()=>{
  const result= await getStudentCourseListPersonalServer(searchBody);
  tableData.value=result.data.items;
  allNumber.value=result.data.total;
}
searchButton()

const delCourse=(row)=>{
  ElMessageBox.confirm(
    '你确认要删除该选课吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: "warning",
    })
    .then(async () => {
      await delCourseChooseServer(row);
      await searchButton();
      ElMessage.success('取选成功',row)
    })
    .catch(() => {
      ElMessage.info('已取消')
    })
}
const reset=()=>{
  searchBody.value={}
}
</script>

<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-input v-model="searchBody.courseNo" style="width: 220px;" placeholder="课程编号" :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column type="index" width="60px" label="序号"/>
        <el-table-column prop="courseNo" label="课程编号" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="{ row }">
            <el-button v-if="row.score==null||row.score==''" type="danger" size="small" @click="delCourse(row)">
              取消选课
            </el-button>

            <el-button v-else  size="small" disabled="true">
              取消选课
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <!--分页插件-->
      <el-pagination style="float: right;
      margin-top: 20px" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[3, 5, 10]"
        :disabled="disabled" :background="background" layout="prev, pager, next, jumper,total, sizes" :total="allNumber"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
  </div>
</template>

<style scoped></style>