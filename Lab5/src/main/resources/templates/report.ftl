<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Image Repository Report</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid black; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        img { max-width: 150px; max-height: 150px; }
    </style>
</head>
<body>
<h1>Image Repository Report</h1>
<table>
    <tr>
        <th>Name</th>
        <th>Date</th>
        <th>Tags</th>
        <th>Image</th>
    </tr>
    <#list images as img>
        <tr>
            <td>${img.name()}</td>
            <td>${img.date()}</td>
            <td>${img.tags()?join(", ")}</td>
            <td><img src="file://${img.location()}" alt="Image"></td>
        </tr>
    </#list>
</table>
</body>
</html>
