#!/bin/bash
branch_name=$(git symbolic-ref --short HEAD)
pattern="^(feature|component|fix|hotfix|release|chore|test)/[a-z0-9\-]+$"
if [[ ! "$branch_name" =~ $pattern ]]; then
echo ""
echo "❌ Invalid branch name: '$branch_name'"
echo "👉 Must match pattern: type/branch-name"
echo "✅ Allowed types: feature, component, fix, hotfix, release, chore, test"
echo "📌 Example: feature/add-login-screen"
exit 1
fi