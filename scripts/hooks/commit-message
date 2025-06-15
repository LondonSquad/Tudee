#!/bin/bash
commit_msg_file=$1
commit_msg=$(cat "$commit_msg_file")
pattern="^(feat|comp|fix|refactor|test|chore|ci): [a-z0-9].*"
if [[ ! "$commit_msg" =~ $pattern ]]; then
echo ""
echo "❌ Invalid commit message!"
echo "👉 Must match: type: message"
echo "✅ Allowed types: feat, comp, fix, refactor, test, chore, ci"
echo "📌 Example: feat: add login screen"
exit 1
fi