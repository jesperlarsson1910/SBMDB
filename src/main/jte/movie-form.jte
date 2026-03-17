@import org.example.sbmdb.entity.dto.MovieDTO
@import gg.jte.Content

@param MovieDTO movie = null

!{String pageTitle = movie != null ? movie.title() : "Add Movie";}
!{String backUrl = movie != null ? "/movies/" + movie.id() : "/";}
!{String backLabel = movie != null ? "Back to " + movie.title() : "Back to Movies";}
!{String formAction = movie != null ? "/movies/" + movie.id() + "/edit" : "/movies/create";}
!{String submitLabel = movie != null ? "Save Changes" : "Add Movie";}
!{String titleValue = movie != null && movie.title() != null ? movie.title() : "";}
!{String directorsValue = movie != null && movie.directors() != null ? String.join(", ", movie.directors()) : "";}
!{String descriptionValue = movie != null && movie.description() != null ? movie.description() : "";}
!{String runningTimeValue = movie != null && movie.runningTime() != null ? String.valueOf(movie.runningTime()) : "";}
!{String releaseYearValue = movie != null && movie.releaseYear() != null ? movie.releaseYear().toString() : "";}

@template.layout(title = pageTitle, content = @`

    <div style="margin-bottom: 0.75rem;">
        <a href="${backUrl}" style="font-size: 0.8rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.08em;">
            ← ${backLabel}
        </a>
    </div>

    <div class="page-header">
        <h1 class="page-title">${pageTitle}</h1>
    </div>

    <form method="post" action="${formAction}" style="max-width: 680px;">

        <div class="form-group">
            <label for="title">Title</label>
            <input type="text" id="title" name="title" required value="${titleValue}"/>
        </div>

        <div class="form-group">
            <label for="directors">Directors <span style="color: var(--text-muted); font-size: 0.7rem;">(comma separated)</span></label>
            <input type="text" id="directors" name="directors" required value="${directorsValue}"/>
        </div>

        <div class="form-group">
            <label for="description">Description <span style="color: var(--text-muted); font-size: 0.7rem;">(optional)</span></label>
            <textarea id="description" name="description">${descriptionValue}</textarea>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;">
            <div class="form-group">
                <label for="runningTime">Running Time <span style="color: var(--text-muted); font-size: 0.7rem;">(minutes)</span></label>
                <input type="number" id="runningTime" name="runningTime" required min="1" value="${runningTimeValue}"/>
            </div>

            <div class="form-group">
                <label for="releaseYear">Release Date</label>
                <input type="date" id="releaseYear" name="releaseYear" required value="${releaseYearValue}"/>
            </div>
        </div>

        <div style="display: flex; gap: 0.75rem; margin-top: 0.5rem;">
            <button type="submit" class="btn btn-primary">${submitLabel}</button>
            <a href="${backUrl}" class="btn btn-ghost">Cancel</a>
        </div>
    </form>
`)