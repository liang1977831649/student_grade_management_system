<script setup>
import {ref} from "vue";
import {getStudentCourseListServer,delCourseChooseServerByManager} from "@/api/studentCourse.js"
import { Search } from '@element-plus/icons-vue'
import { ElMessage,ElMessageBox } from 'element-plus';


const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)

const searchBody=ref({
  courseNo:'',
  studentName:'',
  pageNum:1,
  pageSize:3
})

const handleCurrentChange = async (number) => {
  console.log("current page: ", number)
  searchBody.value.pageNum = number
  await searchButton()
}
const handleSizeChange = async (number) => {
  console.log(" items per page", number)
  searchBody.value.pageSize = number;
  await searchButton()
}

const tableData=ref([
  {
    courseNo:'1234',
    courseName:'高等数学',
    studentNo:'110',
    studentName:'张三'
  },
  {
    courseNo:'1234',
    courseName:'高等数学',
    studentNo:'110',
    studentName:'张三'
  },
  {
    courseNo:'1234',
    courseName:'高等数学',
    studentNo:'110',
    studentName:'张三'
  },
])

const searchButton=async ()=>{
  const result=await getStudentCourseListServer(searchBody);
  tableData.value=result.data.items;
  allNumber.value=result.data.total;
}
searchButton()
const delCourse=(row)=>{
  ElMessageBox.confirm(
    '你确认要删除该选课信息吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: "warning",
    })
    .then(async () => {
      await delCourseChooseServerByManager(row);
      await searchButton();
      ElMessage.success('删除成功')

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
      <el-input v-model="searchBody.studentName" style="width: 220px;" placeholder="学生姓名" :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column type="index" width="60px" label="序号" />
        <el-table-column prop="courseNo" label="课程编号" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="studentId" label="学生编号" />
        <el-table-column prop="studentName" label="学生名称" />
        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="{ row }">
            <el-button type="danger" size="small" @click="delCourse(row)">
              删除
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