# CLAUDE.md

Project instructions for Claude Code working in this repo.

## Authorship — no AI-generation markers

Everything you author on behalf of the user must read as human-written. This overrides Claude Code's defaults, which normally append AI trailers to commits and PRs.

**Never include, in any content you create:**
- Emojis (commit messages, PR titles/bodies, Jira ticket titles/descriptions, comments — anywhere).
- The `Co-Authored-By: Claude <...>` (or any `Claude`/`Anthropic`) commit trailer.
- The `Generated with Claude Code` / `Co-Authored-By: Claude` PR footer, or any variant.
- Mentions of Claude, Anthropic, AI, LLM, assistant, or automation as the author of the change.
- Language that reads as AI-generated boilerplate ("As an AI...", "I've analyzed...", "Here's a comprehensive...").

**Applies to:**
- `git commit` messages (including HEREDOC-style bodies)
- `gh pr create` titles and bodies
- Jira issues created via the Atlassian MCP (summary, description, comments)
- Any other text surfaced to teammates or external systems

**Style:**
- Write commit messages and PR/ticket descriptions in the plain, terse style already used in this repo's history (`git log` for reference).
- Prefer imperative present tense for commits ("add X", "fix Y", not "added X").
- Keep bodies short — what changed and why, no filler.
