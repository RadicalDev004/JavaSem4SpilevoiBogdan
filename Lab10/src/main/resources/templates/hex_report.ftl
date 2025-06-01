<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hex Game Report - Game ${gameId}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f8f8f8;
            padding: 20px;
        }
        .header {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Hex Game Report</h1>
    <p><strong>Game ID:</strong> ${gameId}</p>
    <p><strong>Players:</strong> ${user1} vs ${user2}</p>
    <p><strong>Winner:</strong> ${winner}</p>
</div>

<svg width="1000" height="1000">
    <#list hexes as hex>
        <polygon points="${hex.points}" fill="${hex.fill}" stroke="black" stroke-width="1"/>
    </#list>
</svg>
</body>
</html>
