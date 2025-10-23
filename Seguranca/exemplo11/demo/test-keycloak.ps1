# Script de teste do Keycloak + Spring Boot API
Write-Host "`n=== TESTE 1: Obter token do ADMIN ===" -ForegroundColor Cyan

$tokenAdmin = (curl -Method POST `
  -Uri "http://localhost:8080/realms/demo/protocol/openid-connect/token" `
  -Headers @{ 'Content-Type' = 'application/x-www-form-urlencoded' } `
  -Body "grant_type=password&client_id=spring-api&client_secret=my-spring-api-secret&username=admin&password=admin123" `
  | ConvertFrom-Json).access_token

if ($tokenAdmin) {
    Write-Host "✅ Token Admin obtido com sucesso!" -ForegroundColor Green
    Write-Host "Token (primeiros 50 chars): $($tokenAdmin.Substring(0,50))..." -ForegroundColor Gray
} else {
    Write-Host "❌ Erro ao obter token do admin" -ForegroundColor Red
    exit 1
}

Write-Host "`n=== TESTE 2: Obter token do USER ===" -ForegroundColor Cyan

$tokenUser = (curl -Method POST `
  -Uri "http://localhost:8080/realms/demo/protocol/openid-connect/token" `
  -Headers @{ 'Content-Type' = 'application/x-www-form-urlencoded' } `
  -Body "grant_type=password&client_id=spring-api&client_secret=my-spring-api-secret&username=user&password=user123" `
  | ConvertFrom-Json).access_token

if ($tokenUser) {
    Write-Host "✅ Token User obtido com sucesso!" -ForegroundColor Green
    Write-Host "Token (primeiros 50 chars): $($tokenUser.Substring(0,50))..." -ForegroundColor Gray
} else {
    Write-Host "❌ Erro ao obter token do user" -ForegroundColor Red
    exit 1
}

Write-Host "`n=== TESTE 3: Endpoint público (sem autenticação) ===" -ForegroundColor Cyan
$response = curl -s http://localhost:8081/public
Write-Host "Resposta: $response" -ForegroundColor Yellow

Write-Host "`n=== TESTE 4: Endpoint /user com token USER ===" -ForegroundColor Cyan
$response = curl -s -H "Authorization: Bearer $tokenUser" http://localhost:8081/user
Write-Host "Resposta: $response" -ForegroundColor Yellow

Write-Host "`n=== TESTE 5: Endpoint /admin com token USER (deve falhar 403) ===" -ForegroundColor Cyan
try {
    $response = curl -s -H "Authorization: Bearer $tokenUser" http://localhost:8081/admin 2>&1
    Write-Host "Resposta: $response" -ForegroundColor Red
} catch {
    Write-Host "❌ 403 Forbidden (esperado - user não tem role admin)" -ForegroundColor Green
}

Write-Host "`n=== TESTE 6: Endpoint /admin com token ADMIN (deve funcionar) ===" -ForegroundColor Cyan
$response = curl -s -H "Authorization: Bearer $tokenAdmin" http://localhost:8081/admin
Write-Host "Resposta: $response" -ForegroundColor Yellow

Write-Host "`n=== TESTE 7: Endpoint /user sem token (deve falhar 401) ===" -ForegroundColor Cyan
try {
    $response = curl -s http://localhost:8081/user 2>&1
    Write-Host "Resposta: $response" -ForegroundColor Red
} catch {
    Write-Host "❌ 401 Unauthorized (esperado)" -ForegroundColor Green
}

Write-Host "`n=== TODOS OS TESTES CONCLUÍDOS ===" -ForegroundColor Cyan
Write-Host "`nTokens salvos nas variáveis:" -ForegroundColor Gray
Write-Host "  `$tokenAdmin - use para testar endpoints admin" -ForegroundColor Gray
Write-Host "  `$tokenUser - use para testar endpoints user" -ForegroundColor Gray
