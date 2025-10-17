# -------------------------------
# GITHUB REPOSITORY SETUP SCRIPT
# by ChatGPT 💅
# -------------------------------

Write-Host "💫 GitHub Repository Setup Starting..." -ForegroundColor Cyan

# Ask for your GitHub token (fine-grained, with repo creation permissions)
$token = Read-Host "Paste your GitHub Fine-Grained Access Token"

# Log in with your token
Write-Host "🔐 Authenticating with GitHub CLI..."
$token | gh auth login --with-token

# Configure Git to use GitHub CLI credentials
Write-Host "⚙️ Linking Git and GitHub CLI credentials..."
gh auth setup-git

# Show auth status
gh auth status

# Initialize repo (if not already)
if (-not (Test-Path ".git")) {
    Write-Host " Initializing local git repository..."
    git init
}

# Stage and commit
Write-Host "📦 Adding and committing files..."
git add .
git commit -m "Initial commit from PowerShell setup script" 2>$null

# Ask for repo name
$repoName = Read-Host "Enter GitHub repository name (default: current folder)"
if ([string]::IsNullOrWhiteSpace($repoName)) {
    $repoName = Split-Path -Leaf (Get-Location)
}

# Ask for visibility
$visibility = Read-Host "Visibility (public/private) [default: private]"
if ([string]::IsNullOrWhiteSpace($visibility)) {
    $visibility = "private"
}

# Try creating and pushing
Write-Host "🚀 Creating GitHub repo '$repoName' and pushing code..."
gh repo create $repoName --$visibility --source=. --remote=origin --push

Write-Host "✅ Done! Your repo is live at:" -ForegroundColor Green
gh repo view --web
