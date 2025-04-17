<%@ include file="/header.jsp" %>
<h2>Course List</h2>
<table border="1">
  <tr>
    <th>ID</th>
    <th>Code</th>
    <th>Name</th>
    <th>Credits</th>
    <th>Actions</th>
  </tr>
  <c:forEach var="course" items="${courses}">
    <tr>
      <td>${course.id}</td>
      <td>${course.code}</td>
      <td>${course.name}</td>
      <td>${course.credits}</td>
      <td>
        <a href="${pageContext.request.contextPath}/course/view?id=${course.id}">View</a>
        <a href="${pageContext.request.contextPath}/course/edit?id=${course.id}">Edit</a>
        <a href="${pageContext.request.contextPath}/course/delete?id=${course.id}"
           onclick="return confirm('Are you sure?')">Delete</a>
      </td>
    </tr>
  </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/course/new" class="btn">Add New Course</a>
<%@ include file="/footer.jsp" %>